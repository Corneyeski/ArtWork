package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import artwork.domain.enumeration.ToolType;

/**
 * A Tool.
 */
@Entity
@Table(name = "tool")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tool implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private ToolType type;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tool_user_ext",
               joinColumns = @JoinColumn(name="tools_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="user_exts_id", referencedColumnName="id"))
    private Set<UserExt> userExts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public Tool active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Tool time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public Tool name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Tool description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ToolType getType() {
        return type;
    }

    public Tool type(ToolType type) {
        this.type = type;
        return this;
    }

    public void setType(ToolType type) {
        this.type = type;
    }

    public Set<UserExt> getUserExts() {
        return userExts;
    }

    public Tool userExts(Set<UserExt> userExts) {
        this.userExts = userExts;
        return this;
    }

    public Tool addUserExt(UserExt userExt) {
        this.userExts.add(userExt);
        userExt.getTools().add(this);
        return this;
    }

    public Tool removeUserExt(UserExt userExt) {
        this.userExts.remove(userExt);
        userExt.getTools().remove(this);
        return this;
    }

    public void setUserExts(Set<UserExt> userExts) {
        this.userExts = userExts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tool tool = (Tool) o;
        if (tool.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tool.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tool{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", time='" + getTime() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
