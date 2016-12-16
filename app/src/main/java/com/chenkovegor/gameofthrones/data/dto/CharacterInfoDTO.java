package com.chenkovegor.gameofthrones.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.chenkovegor.gameofthrones.data.storage.models.Character;

public class CharacterInfoDTO implements Parcelable {

    private String name;
    private String houseName;
    private String born;
    private String father;
    private String mother;
    private String aliases;
    private String titles;
    private String words;

    public CharacterInfoDTO(Character character, String words) {
        this.name = character.getName();
        this.houseName = character.getHouseName();
        this.born = character.getBorn();
        this.father = character.getFather();
        this.mother = character.getMother();
        this.aliases = character.getAliases();
        this.titles = character.getTitles();
        this.words = words;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }


    protected CharacterInfoDTO(Parcel in) {
        name = in.readString();
        houseName = in.readString();
        born = in.readString();
        father = in.readString();
        mother = in.readString();
        aliases = in.readString();
        titles = in.readString();
        words = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(houseName);
        dest.writeString(born);
        dest.writeString(father);
        dest.writeString(mother);
        dest.writeString(aliases);
        dest.writeString(titles);
        dest.writeString(words);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CharacterInfoDTO> CREATOR = new Creator<CharacterInfoDTO>() {
        @Override
        public CharacterInfoDTO createFromParcel(Parcel in) {
            return new CharacterInfoDTO(in);
        }

        @Override
        public CharacterInfoDTO[] newArray(int size) {
            return new CharacterInfoDTO[size];
        }
    };

}
