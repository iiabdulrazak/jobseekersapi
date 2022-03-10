package com.jobseekers.mc1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jobseekers.mc1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JsdataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jsdata.class);
        Jsdata jsdata1 = new Jsdata();
        jsdata1.setId(1L);
        Jsdata jsdata2 = new Jsdata();
        jsdata2.setId(jsdata1.getId());
        assertThat(jsdata1).isEqualTo(jsdata2);
        jsdata2.setId(2L);
        assertThat(jsdata1).isNotEqualTo(jsdata2);
        jsdata1.setId(null);
        assertThat(jsdata1).isNotEqualTo(jsdata2);
    }
}
