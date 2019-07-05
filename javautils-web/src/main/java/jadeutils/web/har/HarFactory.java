package jadeutils.web.har;

import java.util.List;
import java.util.Map;

public interface HarFactory<O, A> {


    public Map<String, String> genHeaders(A jArr);

    public List<HarCookie> genCookies(A ckArr);

    public List<String[]> genQueryString(A qsArr);

    public HarPostData genPostData(O pdJo);

    public HarRequest genRequest(O reqJo);

    public HarResponse genResponse(O rspJo);

    public List<HarEntry> genEntries(A entArr);

}
