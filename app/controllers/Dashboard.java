package controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.*;
import play.mvc.With;

import java.util.*;

/**
 * Created by enkhamgalan on 1/22/15.
 */
@With(Secure.class)
@Check(Consts.permissionDashboard)
public class Dashboard extends CRUD {
    public static void index(Long chatUserId) {
        DashboardManagement dashboardManagement = DashboardManagement.findById(1L);
        User user = Users.getUser();
        boolean admin = (user.getUserPermissionType(Consts.permissionFileShare) == 3);
        int postSize = Post.find("SELECT DISTINCT p FROM tb_post p LEFT JOIN p.seeUsers AS u WHERE p.seeAll=true OR (p.owner.id=?1 OR (u.post.id=p.id AND u.user.id=?2))", user.id, user.id).fetch().size();
        int total_groups = postSize / 10;
        Date today = Functions.convertHourNull(new Date());
        Date next = Functions.PrevNextDay(today, 1);
        Date yesterday;
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 19) yesterday = today;
        else yesterday = Functions.PrevNextDay(today, -1);
        List<Post> posts = Post.find("SELECT DISTINCT p FROM tb_post p LEFT JOIN p.seeUsers AS u WHERE (p.seeAll=true OR (p.owner.id=?1 OR (u.post.id=p.id AND u.user.id=?2))) ORDER BY p.pin desc, p.activeDate desc", user.id, user.id).fetch(1, 10);
        render(dashboardManagement, posts, total_groups, admin, yesterday,chatUserId);
    }

    public static void PinPost(Long postId, Long flag) {
        Post pinnedPost = Post.findById(postId);
        if (flag == 0)
            pinnedPost.pin = false;
        else {
            Post findPin = Post.find("pin=true").first();
            if (findPin != null && findPin.pin) {
                findPin.pin = false;
                findPin._save();
            }
            pinnedPost.pin = true;
        }
        pinnedPost._save();
    }

    public static void coverImage(String fileData) {
        DashboardManagement dashboardManagement = DashboardManagement.findById(1L);
        if (dashboardManagement == null) dashboardManagement = new DashboardManagement();
        dashboardManagement.imageDir = FileUploader.decodeToImage(Consts.uploadCoverImagePath, fileData.substring(22, fileData.length()));
        dashboardManagement._save();
    }

    public static void addPost(String data) {
        JsonParser parser = new JsonParser();
        JsonElement eventElement = parser.parse(data);
        JsonObject obj = eventElement.getAsJsonObject();
        String type = obj.get("type").getAsString();
        Post post = new Post();
        post.attaches = new ArrayList<PostAttach>();
        post.seeUsers = new ArrayList<PostSeeUser>();
        JsonArray attaches = obj.get("attach").getAsJsonArray();
        post.type = type;
        Long id;
        JsonElement notielEment = obj.get("notification");
        User owner = Users.getUser();
        List<User> notiEventUsers = new ArrayList<User>();
        List<User> notiPostUsers = new ArrayList<User>();
        boolean seeAll = false;
        if (type.equals("post")) {
            attaches = obj.get("attach").getAsJsonArray();
            JsonArray users = obj.get("users").getAsJsonArray();
            post.owner = owner;
            post.content = obj.get("text").getAsString();
            for (JsonElement element : users) {
                String seeUserId = element.getAsString();
                if (seeUserId.charAt(0) == 'u') {
                    User user = User.findById(Long.parseLong(seeUserId.substring(2)));
                    post.seeUsers.add(new PostSeeUser(user, post));
                    notiPostUsers.add(user);
                } else if (seeUserId.charAt(0) == 't') {
                    UserTeam userBag = UserTeam.findById(Long.parseLong(seeUserId.substring(2)));
                    for (User us : userBag.users) {
                        post.seeUsers.add(new PostSeeUser(us, post));
                        notiPostUsers.add(us);
                    }
                } else {
                    seeAll = true;
                    break;
                }
            }
            post.seeAll = seeAll;

        } else if (type.equals("question")) {
            post.content = obj.get("question").getAsString();
            post.owner = owner;
            post.seeAll = true;
            post.choices = new ArrayList<PostChoice>();
            JsonArray choices = obj.get("choices").getAsJsonArray();
            for (JsonElement choice : choices) {
                PostChoice qChoice = new PostChoice();
                qChoice.post = post;
                qChoice.choice = choice.getAsString();
                post.choices.add(qChoice);
            }
        }
        for (JsonElement element : attaches) {
            JsonObject attachobj = element.getAsJsonObject();
            post.attaches.add(new PostAttach(attachobj.get("filename").getAsString(), attachobj.get("filedir").getAsString()
                    , attachobj.get("extension").getAsString(), null, post));
        }
        post.create();
        if (notiEventUsers.size() > 0) {
            UserLiveRoom.get().notification(owner, "PostEvent", notiEventUsers, post.id);
        }
        if (notielEment != null && notielEment.getAsBoolean()) {
            if (notiPostUsers.size() == 0) {
                List<User> users = User.find("active=true").fetch();
                notiPostUsers.addAll(users);
            }
            UserLiveRoom.get().notification(owner, "PostNew", notiPostUsers, post.id);
        }
        List<Post> posts = new ArrayList<Post>();
        posts.add(post);
        render(posts);
    }

    public static void deletePost(Long id) {
        String pinPost = "";
        try {
            Post post = Post.findById(id);
            for (PostAttach attach : post.attaches) Functions.deleteUploadFile(attach.dir, attach.extension);
            if (post.pin)
                pinPost = post.pin.toString();
            post.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderText(pinPost);
    }

    public static void addComment(String comment, Long postId) {
        Post post = Post.findById(postId);
        User owner = Users.getUser();
        PostComment postComment = new PostComment(comment, post, owner);
        postComment.create();
        post.activeDate = postComment.createdDate;
        post._save();
        List<PostComment> comments = new ArrayList<PostComment>();
        comments.add(postComment);
        if (post.owner.id != owner.id) {
            List<User> users = new ArrayList<User>();
            users.add(post.owner);
            UserLiveRoom.get().notification(owner, "Post", users, post.id);
        }
        render(post, comments);
    }

    public static void AddChoice(Long postId, String choice) {
        Post post = Post.findById(postId);
        PostChoice temp = PostChoice.find("choice=?1", choice).first();
        if (temp == null) {
            PostChoice postChoice = new PostChoice();
            postChoice.choice = choice;
            postChoice.post = post;
            postChoice.create();
        }
    }

    public static void showVoteSize(Long choiceId) {
        PostChoice choice = PostChoice.findById(choiceId);
        List<PostChoiceVote> votes = choice.getVotesAll();
        JsonArray array = new JsonArray();
        for (PostChoiceVote qc : votes) {
            JsonObject jobj = new JsonObject();
            jobj.addProperty("user", qc.user.toString());
            jobj.addProperty("pic", qc.user.profilePicture);
            array.add(jobj);
        }
        renderJSON(array);
    }

    public static void addVote(boolean flag, Long choiceId, Long postId) {
        User uc = Users.getUser();
        PostChoiceVote choiceVote = PostChoiceVote.find("choice.id=?1 and user.id=?2", choiceId, uc.id).first();
        if (flag) {
            if (choiceVote == null) {
                PostChoice choice = PostChoice.findById(choiceId);
                PostChoiceVote vote = new PostChoiceVote();
                vote.choice = choice;
                vote.user = Users.getUser();
                vote.create();
            }
        } else if (choiceVote != null) choiceVote.delete();
        List<PostChoice> choices = reArrangeVote(postId);
        JsonArray array = new JsonArray();
        for (PostChoice qc : choices) {
            JsonObject jobj = new JsonObject();
            jobj.addProperty("id", qc.id);
            jobj.addProperty("procent", qc.procent);
            array.add(jobj);
        }
        renderJSON(array);
    }

    public static List<PostChoice> reArrangeVote(Long postId) {
        List<PostChoice> choices = PostChoice.find("post.id=?1", postId).fetch();
        int temp = 0;
        int index = -1, i = 0;
        for (PostChoice postChoice : choices) {
            if (postChoice.getVotesSize() > temp) {
                temp = postChoice.getVotesSize();
                index = i;
            }
            i++;
        }
        for (i = 0; i < choices.size(); i++) {
            if (i == index)
                choices.get(i).procent = 100;
            else if (temp == 0)
                choices.get(i).procent = 0;
            else
                choices.get(i).procent = choices.get(i).getVotesSize() * 100 / temp;
            choices.get(i)._save();
        }
        return choices;
    }

    public static void showMoreComment(Long postId, String type) {
        List<PostComment> comments;
        if (type.equals("show"))
            comments = PostComment.find("post.id=?1 ORDER BY createdDate", postId).fetch();
        else {
            comments = PostComment.find("post.id=?1 ORDER BY createdDate desc", postId).fetch(1, 2);
            Collections.reverse(comments);
        }
        Post post = Post.findById(postId);
        render("Dashboard/addComment.html", post, comments);
    }


    public static void likePost(Long postId) {
        Post post = Post.findById(postId);
        User user = Users.getUser();
        String userName = user.toString();
        String like = "fa fa-thumbs-o-up";
        if (post.seeAll) {
            if (!post.likeUsers.contains(userName)) {
                post.likeUsers += (userName + "\n");
                post.likes += 1;
                post._save();
                like = "fa fa-thumbs-up";
            } else {
                if (post.likes.intValue() > 0) {
                    post.likeUsers = post.likeUsers.replaceAll(userName + "\n", "");
                    post.likes -= 1;
                    post._save();
                }
            }
        } else {
            PostSeeUser seeUser = PostSeeUser.find("post.id=?1 AND user.id=?2", postId, Users.getUser().id).first();
            if (seeUser != null) {
                if (!seeUser.likeThisPost) {
                    seeUser.likeThisPost = true;
                    seeUser._save();
                    post.likeUsers += (userName + "\n");
                    post.likes += 1;
                    post._save();
                    like = "fa fa-thumbs-up";
                } else {
                    if (post.likes.intValue() > 0) {
                        post.likeUsers = post.likeUsers.replaceAll(userName + "\n", "");
                        post.likes -= 1;
                        post._save();
                    }
                }
            } else if (post.owner.id == user.id) {
                if (!post.likeUsers.contains(userName)) {
                    post.likeUsers += (userName + "\n");
                    post.likes += 1;
                    post._save();
                    like = "fa fa-thumbs-up";
                } else {
                    if (post.likes.intValue() > 0) {
                        post.likeUsers = post.likeUsers.replaceAll(userName + "\n", "");
                        post.likes -= 1;
                        post._save();
                    }
                }
            }
        }
        JsonObject data = new JsonObject();
        data.addProperty("likes", post.likes);
        data.addProperty("likeUsers", post.likeUsers);
        data.addProperty("like", like);
        renderJSON(data);
    }

    public static void loadMorePost(Integer groupNumber) {
        Long userId = Users.getUser().id;
        List<Post> posts = Post.find("SELECT DISTINCT p FROM tb_post p LEFT JOIN p.seeUsers AS u WHERE (p.seeAll=true OR (p.owner.id=?1 OR (u.post.id=p.id AND u.user.id=?2))) AND p.event.id=null  ORDER BY p.pin desc, p.activeDate desc", userId, userId).fetch(groupNumber + 1, 10);
        render("Dashboard/addPost.html", posts);
    }

    public static void readMorePost(Long id, boolean type, Long number) {
        Post post = Post.findById(id);
        if (type)
            renderText(post.content);
        else {
            render("Dashboard/imageShowModal.html", post, number);
        }
    }

    public static void deleteComment(Long commentId) {
        try {
            PostComment comment = PostComment.findById(commentId);
            comment.delete();
            renderText("Устлаа");
        } catch (Exception e) {
            e.printStackTrace();
            renderText("Устсангүй");
        }
    }

    public static void saveEditPost(Long postId, String text) {
        Post post = Post.findById(postId);
        post.content = text;
        post._save();
    }

    public static void saveEditComment(Long commentId, String text) {
        PostComment comment = PostComment.findById(commentId);
        comment.comment = text;
        comment._save();
    }

    public static void getPostContent(Long postId) {
        Post post = Post.findById(postId);
        renderHtml(post.content);
    }
}
