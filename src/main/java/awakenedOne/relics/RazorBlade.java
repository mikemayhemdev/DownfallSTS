package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.ManaburnPower;
import awakenedOne.powers.OnLoseEnergyPower;
import awakenedOne.powers.UltimateHexDebuff;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class RazorBlade extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("RazorBlade");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("RazorBlade.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("RazorBlade.png"));

    private static final int AMOUNT = 4;

    public RazorBlade() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    //Razor Blade

    public void onExhaust(AbstractCard card) {
        if (card.type == AbstractCard.CardType.CURSE) {
            this.flash();
            Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while (var2.hasNext()) {
                AbstractMonster mo = (AbstractMonster) var2.next();
                if (!mo.isDead) {
                    this.addToTop(new RelicAboveCreatureAction(mo, this));
                    this.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new ManaburnPower(mo, AMOUNT), AMOUNT, true));
                }
            }


            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;

                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (p instanceof OnLoseEnergyPower) {
                            ((OnLoseEnergyPower) p).LoseEnergyAction(1);
                        }
                    }

                    for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
                        if (!m2.isDead && !m2.isDying) {
                            for (AbstractPower p : m2.powers) {
                                if (p instanceof OnLoseEnergyPower) {
                                    ((OnLoseEnergyPower) p).LoseEnergyAction(1);
                                }
                            }
                        }

                        for (AbstractRelic p : AbstractDungeon.player.relics) {
                            if (p instanceof OnLoseEnergyRelic) {
                                ((OnLoseEnergyRelic) p).LoseEnergyAction(1);
                            }
                        }
                    }
                }
            });
        }
    }
}