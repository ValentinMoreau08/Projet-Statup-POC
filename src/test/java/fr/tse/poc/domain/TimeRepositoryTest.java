package fr.tse.poc.domain;

import fr.tse.poc.dao.TimeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class TimeRepositoryTest {

    @Autowired
    private TimeRepository timeRepository;

    @Test
    public void saveAndDeleteTime() {
        List<Time> times = timeRepository.findAll();
        int prevSize = times.size();

        Time time = new Time(10, new Date());
        time = timeRepository.save(time);

        times = timeRepository.findAll();

        Assertions.assertNotNull(times);
        Assertions.assertEquals(prevSize + 1, times.size());

        Time newTime = timeRepository.findById(time.getId()).orElse(null);
        Assertions.assertEquals(newTime, time);

        timeRepository.delete(time);

        times = timeRepository.findAll();
        Assertions.assertNotNull(times);
        Assertions.assertEquals(prevSize, times.size());
    }
}
