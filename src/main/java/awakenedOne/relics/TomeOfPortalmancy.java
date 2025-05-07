package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.UltimateHexDebuff;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.*;

public class TomeOfPortalmancy extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("TomeOfPortalmancy");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("TomeOfPortalmancy.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("TomeOfPortalmancy.png"));

    private static final int AMOUNT = 3;

    public TomeOfPortalmancy() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
        AbstractCard q = new VoidCard();
        tips.add(new CardPowerTip(q));
    }

    public void onExhaust(AbstractCard card) {
        if (card.cardID == VoidCard.ID) {
            this.flash();
            Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while (var2.hasNext()) {
                AbstractMonster mo = (AbstractMonster) var2.next();
                if (!mo.isDead) {
                    if (!mo.hasPower(UltimateHexDebuff.POWER_ID)) {
                        HexCurse(AMOUNT, mo, AbstractDungeon.player);
                        this.addToBot(new VFXAction(new GiantEyeEffect(mo.hb.cX, mo.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.3F, 1.0F, 0.0F))));
                        this.addToTop(new RelicAboveCreatureAction(mo, this));
                    }
                }
            }

        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}
