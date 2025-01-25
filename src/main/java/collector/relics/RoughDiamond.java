package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import downfall.util.TextureLoader;

public class RoughDiamond extends CustomRelic {
    public static final String ID = CollectorMod.makeID(RoughDiamond.class.getSimpleName());
    private static final String IMG_PATH = RoughDiamond.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = RoughDiamond.class.getSimpleName() + ".png";
    private boolean triggeredThisTurn = false;


    public RoughDiamond() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    public void atTurnStart() {
        this.triggeredThisTurn = false;
        this.grayscale = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
            if (card.rarity == AbstractCard.CardRarity.RARE) {
                if (!this.triggeredThisTurn) {
                    this.triggeredThisTurn = true;
                    this.grayscale = true;
                flash();
                this.addToBot(new GainEnergyAction(1));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

