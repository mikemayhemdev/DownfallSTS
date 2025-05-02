package awakenedOne.relics;

import automaton.cards.FineTuning;
import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.*;

public class EyeOfTheOccult extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("EyeOfTheOccult");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("EyeOfTheOccult.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("EyeOfTheOccult.png"));

    private static int AMOUNT = 3;

    public EyeOfTheOccult() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
