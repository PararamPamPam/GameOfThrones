package com.chenkovegor.gameofthrones.data.storage.models;

import com.chenkovegor.gameofthrones.data.network.res.HouseRes;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity(active = true, nameInDb = "HOUSE")
public class House {

    @Id
    private Long id;
    @NotNull
    @Unique
    private String name;
    @NotNull
    @Unique
    private String shortName;
    private String words;
    @ToMany(joinProperties = {
            @JoinProperty(name = "shortName", referencedName = "houseName")
    })
    private List<Character> mCharacters;
    /** Used for active entity operations. */
    @Generated(hash = 1167916919)
    private transient HouseDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public House(HouseRes house, String shortName) {
        name = house.getName();
        words = house.getWords();
        this.shortName = shortName;
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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 821939764)
    public synchronized void resetMCharacters() {
        mCharacters = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1048440530)
    public List<Character> getMCharacters() {
        if (mCharacters == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CharacterDao targetDao = daoSession.getCharacterDao();
            List<Character> mCharactersNew = targetDao._queryHouse_MCharacters(shortName);
            synchronized (this) {
                if(mCharacters == null) {
                    mCharacters = mCharactersNew;
                }
            }
        }
        return mCharacters;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 451323429)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHouseDao() : null;
    }

    public String getWords() {
        return this.words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1269809577)
    public House(Long id, @NotNull String name, @NotNull String shortName,
            String words) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.words = words;
    }

    @Generated(hash = 389023854)
    public House() {
    }
}
