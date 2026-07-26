package ${package};

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PlaceholderTest {

    @Test
    void describesItself() {
        assertThat(new Placeholder().describe()).contains("${artifactId}");
    }
}
