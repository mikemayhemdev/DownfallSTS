package downfall.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.RewardGlowEffect;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;
import guardian.characters.GuardianCharacter;
import slimebound.characters.SlimeboundCharacter;
import theHexaghost.HexaMod;
import theHexaghost.TheHexaghost;
import theHexaghost.cards.seals.AbstractSealCard;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class LocalizeHelper {
    public static final UIStrings RunHistoryMonsterNames ;
    public static final UIStrings DonwfallRunHistoryMonsterNames ;

    static {
        RunHistoryMonsterNames = CardCrawlGame.languagePack.getUIString("RunHistoryMonsterNames");
        DonwfallRunHistoryMonsterNames = CardCrawlGame.languagePack.getUIString("downfall:RunHistoryMonsterNames");
    }

    public LocalizeHelper() {

    }
}