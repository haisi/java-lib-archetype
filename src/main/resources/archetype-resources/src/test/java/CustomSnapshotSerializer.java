package ${package};

import au.com.origin.snapshots.jackson3.serializers.v1.DeterministicJackson3SnapshotSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.ZonedDateTime;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

/** Default snapshot serializer: deterministic Jackson3 JSON, but also renders null-valued fields. */
public class CustomSnapshotSerializer extends DeterministicJackson3SnapshotSerializer {

    @Override
    protected JsonMapper.Builder configure(JsonMapper.Builder builder) {
        return super.configure(builder)
                .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
                .changeDefaultPropertyInclusion(inclusion -> inclusion.withValueInclusion(JsonInclude.Include.ALWAYS));
    }

    /** Apply via {@code objectMapper.addMixIn(SomeType.class, IgnoreTypeMixin.class)} to exclude a whole type. */
    @JsonIgnoreType
    public interface IgnoreTypeMixin {}

    /** Apply via {@code objectMapper.addMixIn(YourEntity.class, DateMixin.class)} to hide audit timestamps. */
    public interface DateMixin {

        @JsonIgnore
        ZonedDateTime getCreatedAt();

        @JsonIgnore
        ZonedDateTime createdAt();

        @JsonIgnore
        ZonedDateTime getUpdatedAt();
    }
}
