package github.eddy.game.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * @author edliao
 * 版本记录 ,发送包中带有版本号用来确定客户端与服务器版本是否同步
 */
public class Version {
    private static final short DEFAULT_VERSION = 0;
    @Getter
    @Setter
    private static short currentVersion = DEFAULT_VERSION;
}
