
package com.bavin.mohsen.backnardeban.Classes.RetrofitClasses.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThirdExperimentNum {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("persianLiterature")
    @Expose
    private int persianLiterature;
    @SerializedName("arabic")
    @Expose
    private int arabic;
    @SerializedName("religiousEducation")
    @Expose
    private int religiousEducation;
    @SerializedName("english")
    @Expose
    private int english;
    @SerializedName("geology")
    @Expose
    private int geology;
    @SerializedName("biology")
    @Expose
    private int biology;
    @SerializedName("physics")
    @Expose
    private int physics;
    @SerializedName("mathematics")
    @Expose
    private int mathematics;
    @SerializedName("chemistry")
    @Expose
    private int chemistry;

    public int getPersianLiterature() {
        return persianLiterature;
    }

    public int getArabic() {
        return arabic;
    }

    public int getReligiousEducation() {
        return religiousEducation;
    }

    public int getEnglish() {
        return english;
    }

    public int getGeology() {
        return geology;
    }

    public int getBiology() {
        return biology;
    }

    public int getPhysics() {
        return physics;
    }

    public int getMathematics() {
        return mathematics;
    }

    public int getChemistry() {
        return chemistry;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }


    public String getNameLesson(String name) {
        String number;
        switch (name){
            case "زبان و ادبیات فارسی":
                number= "persianLiterature";
            break;
            case "عربی":
                number= "arabic";
            break;
            case "فرهنگ و معارف اسلامی":
                number= "religiousEducation";
                break;
            case "زبان انگلیسی":
                number= "english";
                break;
                case "زمین شناسی":
                number= "geology";
                break;
            case "زیست شناسی":
                number= "biology";
                break;
            case "فیزیک":
                number= "physics";
                break;
            case "ریاضیات":
                number= "mathematics";
                break;
            case "شیمی":
                number= "chemistry";
                break;
            default:
                number= "";
            break;
        }
        return number;
    }



    public int getNumOfSelect(String name) {
        int number;
        switch (name){
            case "زبان و ادبیات فارسی":
                number= persianLiterature;
                break;
            case "عربی":
                number= arabic;
                break;
            case "فرهنگ و معارف اسلامی":
                number= religiousEducation;
                break;
            case "زبان انگلیسی":
                number= english;
                break;
            case "زمین شناسی":
                number= geology;
                break;
            case "زیست شناسی":
                number= biology;
                break;
            case "فیزیک":
                number= physics;
                break;
            case "ریاضیات":
                number= mathematics;
                break;
            case "شیمی":
                number= chemistry;
                break;
            default:
                number= 0;
                break;
        }
        return number;
    }

}
