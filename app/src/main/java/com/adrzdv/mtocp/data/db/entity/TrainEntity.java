package com.adrzdv.mtocp.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "trains",
        foreignKeys = @ForeignKey(
                entity = DepotEntity.class,
                parentColumns = "id",
                childColumns = "depot_id"
        )
)
public class TrainEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;
    @NonNull
    @ColumnInfo(name = "number")
    private String number;
    @NonNull
    @ColumnInfo(name = "route")
    private String route;
    @NonNull
    @ColumnInfo(name = "depot_id")
    private Integer depotId;
    @NonNull
    @ColumnInfo(name = "is_video")
    private Boolean isVideo;
    @NonNull
    @ColumnInfo(name = "is_progressive")
    private Boolean isProgressive;

    public TrainEntity() {
        id = 0;
        number = "";
        route = "";
        depotId = 0;
        isVideo = false;
        isProgressive = false;
    }

    public TrainEntity(@NonNull Integer id,
                       @NonNull String number,
                       @NonNull String route,
                       @NonNull Integer depotId,
                       @NonNull Boolean isVideo,
                       @NonNull Boolean isProgressive) {
        this.id = id;
        this.number = number;
        this.route = route;
        this.depotId = depotId;
        this.isVideo = isVideo;
        this.isProgressive = isProgressive;

    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getNumber() {
        return number;
    }

    public void setNumber(@NonNull String number) {
        this.number = number;
    }

    @NonNull
    public String getRoute() {
        return route;
    }

    public void setRoute(@NonNull String route) {
        this.route = route;
    }

    @NonNull
    public Integer getDepotId() {
        return depotId;
    }

    public void setDepotId(@NonNull Integer depotId) {
        this.depotId = depotId;
    }

    @NonNull
    public Boolean getVideo() {
        return isVideo;
    }

    public void setVideo(@NonNull Boolean video) {
        isVideo = video;
    }

    @NonNull
    public Boolean getProgressive() {
        return isProgressive;
    }

    public void setProgressive(@NonNull Boolean progressive) {
        isProgressive = progressive;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TrainEntity that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(number, that.number)
                && Objects.equals(route, that.route)
                && Objects.equals(depotId, that.depotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, route, depotId);
    }

    @NonNull
    @Override
    public String toString() {
        return getNumber() + " " + getRoute();
    }
}
