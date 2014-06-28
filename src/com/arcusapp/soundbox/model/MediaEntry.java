/*
 * SoundBox - Android Music Player
 * Copyright (C) 2013 Iván Arcuschin Moreno
 *
 * This file is part of SoundBox.
 *
 * SoundBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * SoundBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SoundBox.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.arcusapp.soundbox.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MediaEntry implements Entry<MediaEntry>, Parcelable {

    private final String ID_JSON = "id";
    private final String VALUE_JSON = "value";
    private final String DETAIL_JSON = "detail";
    private final String TYPE_JSON = "type";

    private String mId;
    private MediaType mType;
    private String mValue;
    private String mDetail;

    public MediaEntry(String id, MediaType type, String value, String detail) {
        this.mId = id;
        this.mType = type;
        this.mValue = value;
        this.mDetail = detail;
    }

    public MediaEntry(String id, MediaType type, String value) {
        this(id, type, value, "");
    }

    public MediaEntry(Parcel parcel) {
        String[] strings = new String[3];
        parcel.readStringArray(strings);
        mId = strings[0];
        mValue = strings[1];
        mDetail = strings[2];

        mType = (MediaType) parcel.readSerializable();
    }

    public MediaEntry(JSONObject jsonObject) {
        try {
            mId = jsonObject.getString(ID_JSON);
            mValue = jsonObject.getString(VALUE_JSON);
            mDetail = jsonObject.getString(DETAIL_JSON);
            mType = MediaType.valueOf(jsonObject.getString(TYPE_JSON));
        } catch (JSONException e) {
            Log.d(MediaEntry.class.getName(), e.getMessage());
        }
    }

    public String getID() {
        return mId;
    }

    public String getValue() {
        return mValue;
    }

    @Override
    public String getDetail() { return mDetail; }

    @Override
    public MediaType getMediaType() { return mType; }

    @Override
    public int compareTo(MediaEntry another) {
        return this.mValue.compareToIgnoreCase(another.getValue());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{mId, mValue, mDetail});
        dest.writeSerializable(mType);
    }

    public static final Parcelable.Creator<MediaEntry> CREATOR = new Parcelable.Creator<MediaEntry>() {
        @Override
        public MediaEntry createFromParcel(Parcel parcel) {
            return new MediaEntry(parcel);
        }

        @Override
        public MediaEntry[] newArray(int size) {
            return new MediaEntry[size];
        }
    };

    public String toJSON(){
        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put(ID_JSON, mId);
            jsonObject.put(VALUE_JSON, mValue);
            jsonObject.put(DETAIL_JSON, mDetail);
            jsonObject.put(TYPE_JSON, mType);

            return jsonObject.toString();
        } catch (JSONException e) {
            Log.d(MediaEntry.class.getName(), e.getMessage());
            return "";
        }
    }
}
