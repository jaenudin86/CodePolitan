/*
 * Copyright (c) 2015 Zelory.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package id.zelory.codepolitan.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class Article implements Parcelable
{
    public final static String TYPE_NEWS = "news";
    public final static String TYPE_KOMIK = "nyankomik";
    public final static String TYPE_MEME = "meme";
    public final static String TYPE_QUOTE = "quotes";

    private int id;
    private String slug;
    private String title;
    private String excerpt;
    private String content;
    private String date;
    private String dateClear;
    private String link;
    private String thumbnailSmall;
    private String thumbnailMedium;
    private boolean bookmarked;
    private boolean readLater;
    private boolean big;
    private String postType;
    private List<Category> category;
    private List<Tag> tags;

    public Article()
    {
        postType = TYPE_NEWS;
    }

    protected Article(Parcel in)
    {
        id = in.readInt();
        slug = in.readString();
        title = in.readString();
        excerpt = in.readString();
        content = in.readString();
        date = in.readString();
        dateClear = in.readString();
        link = in.readString();
        thumbnailSmall = in.readString();
        thumbnailMedium = in.readString();
        bookmarked = in.readByte() != 0;
        readLater = in.readByte() != 0;
        big = in.readByte() != 0;
        postType = in.readString();
        category = in.createTypedArrayList(Category.CREATOR);
        tags = in.createTypedArrayList(Tag.CREATOR);
    }

    public static final Creator<Article> CREATOR = new Creator<Article>()
    {
        @Override
        public Article createFromParcel(Parcel in)
        {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size)
        {
            return new Article[size];
        }
    };

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getSlug()
    {
        return slug;
    }

    public void setSlug(String slug)
    {
        this.slug = slug;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getExcerpt()
    {
        return excerpt;
    }

    public void setExcerpt(String excerpt)
    {
        this.excerpt = excerpt;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getDateClear()
    {
        return dateClear;
    }

    public void setDateClear(String dateClear)
    {
        this.dateClear = dateClear;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getThumbnailSmall()
    {
        return thumbnailSmall;
    }

    public void setThumbnailSmall(String thumbnailSmall)
    {
        this.thumbnailSmall = thumbnailSmall;
    }

    public String getThumbnailMedium()
    {
        return thumbnailMedium;
    }

    public void setThumbnailMedium(String thumbnailMedium)
    {
        this.thumbnailMedium = thumbnailMedium;
    }

    public boolean isBookmarked()
    {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked)
    {
        this.bookmarked = bookmarked;
    }

    public boolean isReadLater()
    {
        return readLater;
    }

    public void setReadLater(boolean readLater)
    {
        this.readLater = readLater;
    }

    public boolean isBig()
    {
        return big;
    }

    public void setBig(boolean big)
    {
        this.big = big;
    }

    public String getPostType()
    {
        return postType;
    }

    public void setPostType(String postType)
    {
        this.postType = postType;
    }

    public List<Category> getCategory()
    {
        return category;
    }

    public void setCategory(List<Category> category)
    {
        this.category = category;
    }

    public List<Tag> getTags()
    {
        return tags;
    }

    public void setTags(List<Tag> tags)
    {
        this.tags = tags;
    }

    @Override
    public int describeContents()
    {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(slug);
        dest.writeString(title);
        dest.writeString(excerpt);
        dest.writeString(content);
        dest.writeString(date);
        dest.writeString(dateClear);
        dest.writeString(link);
        dest.writeString(thumbnailSmall);
        dest.writeString(thumbnailMedium);
        dest.writeByte((byte) (bookmarked ? 1 : 0));
        dest.writeByte((byte) (readLater ? 1 : 0));
        dest.writeByte((byte) (big ? 1 : 0));
        dest.writeString(postType);
        dest.writeTypedList(category);
        dest.writeTypedList(tags);
    }
}
