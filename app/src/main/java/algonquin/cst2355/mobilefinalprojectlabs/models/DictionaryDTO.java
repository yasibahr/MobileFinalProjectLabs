package algonquin.cst2355.mobilefinalprojectlabs.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DictionaryDTO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="termId")
    public long termId; //term id

    @ColumnInfo(name="Term")
    public String term;

    @ColumnInfo(name="Definition1")
    public String def1;

    @ColumnInfo(name="Definition2")
    public String def2;

    @ColumnInfo(name="Definition3")
    public String def3;

    @ColumnInfo(name="Saved")
    public boolean isSaved;

    public DictionaryDTO(){} //empty constructor lets variables be accessed directly

    public DictionaryDTO(String term, String def1, String def2, String def3, boolean saved){
        this.term=term;
        this.def1=def1;
        this.def2=def2;
        this.def3=def3;
        this.isSaved=saved;
    }

    public String getTerm(){return term;}
    public String getDef1(){return def1;}
    public String getDef2(){return def2;}
    public String getDef3(){return def3;}
    public boolean isSaved(){return isSaved;}
}
