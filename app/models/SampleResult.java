package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * Created by Munguntsetseg on 2017-07-18.
 */
@Entity(name = "tb_sample_result")
public class SampleResult extends BaseModel {

    public Long sample_id;

    public String number;

    public String purpose;

    public Date recieve_date;

    public Date result_date;

    public Long vet_sum_id;
//
//    @ManyToOne
//    public SumName sumName;

    public String path;

    public String pdfUrl;

    public boolean type;   //дүнгийн хуудас ашигласан эсэх
//
//    @OneToMany(mappedBy = "sampleResult", cascade = CascadeType.ALL)
//    public List<Meg> megs;

}
