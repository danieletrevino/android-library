/*
Copyright 2009-2015 Urban Airship Inc. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE URBAN AIRSHIP INC ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
EVENT SHALL URBAN AIRSHIP INC OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.urbanairship.push.ian;

import com.urbanairship.Logger;
import com.urbanairship.UAirship;
import com.urbanairship.analytics.Event;
import com.urbanairship.util.UAStringUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An event that is sent the first time an {@link com.urbanairship.push.ian.InAppNotification} is displayed.
 *
 * @hide
 */
public class DisplayEvent extends Event {
    private static final String TYPE = "ian_display";

    private static final String ID = "id";
    private static final String CONVERSION_SEND_ID = "conversion_send_id";

    private final String id;

    /**
     * Default constructor.
     *
     * @param inAppNotification The InAppNotification.
     */
    public DisplayEvent(InAppNotification inAppNotification) {
        id = inAppNotification.getId();
    }

    @Override
    public final String getType() {
        return TYPE;
    }

    @Override
    protected final JSONObject getEventData() {
        JSONObject data = new JSONObject();

        try {
            data.putOpt(ID, id);
            data.putOpt(CONVERSION_SEND_ID, UAirship.shared().getAnalytics().getConversionSendId());
        } catch (JSONException e) {
            Logger.error("DisplayEvent - Error constructing JSON data.", e);
        }

        return data;
    }

    @Override
    public boolean isValid() {
        return !UAStringUtil.isEmpty(id);
    }
}