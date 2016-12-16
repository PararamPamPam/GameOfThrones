package com.chenkovegor.gameofthrones.data.storage.models;

import com.chenkovegor.gameofthrones.data.network.res.CharacterInfoRes;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import org.greenrobot.greendao.DaoException;

import java.util.List;

@Entity(active = true, nameInDb = "CHARACTERS")
public class Character {

    @Id
    private Long id;
    @NotNull
    @Unique
    private String searchURL;
    @NotNull
    @Unique
    private String name;
    private String houseName;
    private String born;
    private String father;
    private String mother;
    private String aliases;
    private String titles;
    /** Used for active entity operations. */
    @Generated(hash = 898307126)
    private transient CharacterDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public Character(CharacterInfoRes CharacterInfo, String houseName) {
        name = CharacterInfo.getName();
        this.houseName = houseName;
        searchURL = CharacterInfo.getUrl().toUpperCase();
        father = CharacterInfo.getFather();
        mother = CharacterInfo.getMother();
        born = CharacterInfo.getBorn();

        aliases = "";
        List<String> aliasesList = CharacterInfo.getAliases();
        for (int i = 0; i < aliasesList.size(); i++) {
            aliases += aliasesList.get(i);
            aliases += i != aliasesList.size() - 1 ? ", " : "";
        }

        titles = "";
        List<String> titlesList = CharacterInfo.getTitles();
        for (int i = 0; i < titlesList.size(); i++) {
            titles += titlesList.get(i);;
            titles += i != titlesList.size() - 1 ? ", " : "";
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 162219484)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCharacterDao() : null;
    }

    public String getTitles() {
        return this.titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getAliases() {
        return this.aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getMother() {
        return this.mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFather() {
        return this.father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getBorn() {
        return this.born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getHouseName() {
        return this.houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchURL() {
        return this.searchURL;
    }

    public void setSearchURL(String searchURL) {
        this.searchURL = searchURL;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1483768658)
    public Character(Long id, @NotNull String searchURL, @NotNull String name,
            String houseName, String born, String father, String mother,
            String aliases, String titles) {
        this.id = id;
        this.searchURL = searchURL;
        this.name = name;
        this.houseName = houseName;
        this.born = born;
        this.father = father;
        this.mother = mother;
        this.aliases = aliases;
        this.titles = titles;
    }

    @Generated(hash = 1853959157)
    public Character() {
    }
}
