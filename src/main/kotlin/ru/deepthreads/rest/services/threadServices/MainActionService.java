package ru.deepthreads.rest.services.threadServices;

import org.springframework.stereotype.Service;
import ru.deepthreads.rest.Constants;
import ru.deepthreads.rest.Utils;
import ru.deepthreads.rest.models.entity.Thread;
import ru.deepthreads.rest.models.views.ThreadView;

import java.time.Instant;
import java.util.ArrayList;

@Service
public class MainActionService {
    public static MainActionService instance = new MainActionService();

    public ThreadView startThread(
            String name,
            String themeColor,
            String iconUrl,
            String coverUrl,
            String backgroundUrl,
            String adminUid
    ) {
        Thread thread = new Thread(
                Utils.INSTANCE.generateObjectId(),
                Instant.now().getEpochSecond(),
                Constants.OBJECTSTATUS.NORMAL,
                name,
                themeColor,
                iconUrl,
                coverUrl,
                backgroundUrl,
                new ArrayList<>(),
                new ArrayList<>(),
                adminUid,
                new ArrayList<>(),
                new ArrayList<>()
        );
        mongoTe
    }

    public static MainActionService getInstance() {
        return instance;
    }
}
