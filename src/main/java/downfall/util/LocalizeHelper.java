package downfall.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

public class LocalizeHelper {
    public static final UIStrings RunHistoryMonsterNames ;
    public static final UIStrings DonwfallRunHistoryMonsterNames ;
    public static final CharacterStrings downfallCharacterSpeech ;

    static {
        RunHistoryMonsterNames = CardCrawlGame.languagePack.getUIString("RunHistoryMonsterNames");
        DonwfallRunHistoryMonsterNames = CardCrawlGame.languagePack.getUIString("downfall:RunHistoryMonsterNames");
        downfallCharacterSpeech = CardCrawlGame.languagePack.getCharacterString("downfall:CharBossSpeech");
    }

    public LocalizeHelper() {

    }
}