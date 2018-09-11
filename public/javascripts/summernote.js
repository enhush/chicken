function init_summernote() {
    $('.summernote').summernote({
        height: 500,
        toolbar: [
            ["style", ["style"]],
            ["style", ["bold", "italic", "underline", "clear"]],
            ["fontsize", ["fontsize"]],
            ["color", ["color"]],
            ["para", ["ul", "ol", "paragraph"]],
            ["height", ["height"]],
            ["table", ["table"]]
        ]
    });
    //$(".note-codable").hide();
    //$("div.note-editable").css("border", "1px solid darkgrey").css("padding", "4px").css("overflow-y", "auto");
}