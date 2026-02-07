package com.artifacts;

import com.artifacts.api.endpoints.get.GetAllMaps;
import com.artifacts.api.endpoints.post.ActionGathering;
import com.artifacts.api.endpoints.post.ActionMove;
import com.artifacts.game.logic.activity.Gathering;
import com.artifacts.game.helpers.GlobalCooldownManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_CHARACTER_LOCKED;
import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GatheringActivityTest {

    @Test
    void Gathering486APIResponseErrorTest() throws Exception {
        var coordinatesMock = new JSONObject().put("data", new JSONArray().put(new JSONObject().put("x", 1).put("y", 2)));
        var moveMock = new JSONObject().put("statusCode", CODE_SUCCESS);
        var code_character_lockedMock = new JSONObject().put("statusCode", CODE_CHARACTER_LOCKED);
        var stopMock = new JSONObject().put("statusCode", 999);

        try (var maps = mockStatic(GetAllMaps.class);
             var move = mockStatic(ActionMove.class);
             var gathering = mockStatic(ActionGathering.class);
             var cooldown = mockStatic(GlobalCooldownManager.class)) {


            //stub
            maps.when(() -> GetAllMaps.getAllMaps(anyString())).thenReturn(coordinatesMock);
            move.when(() -> ActionMove.actionMove(anyString(), anyInt(), anyInt())).thenReturn(moveMock);
            gathering.when(() -> ActionGathering.actionGathering(anyString())).thenReturn(code_character_lockedMock, stopMock);

            //simulation
            Gathering.gather("anyName", "anyActivityLocation", false, false, false, false); //todo update simulation for miningAll etc.

            //verify
            gathering.verify(() -> ActionGathering.actionGathering("anyName"), times(2));
            cooldown.verify(() -> GlobalCooldownManager.globalCooldownManager(eq("anyName"), any()), atLeastOnce());
        }
    }
}

