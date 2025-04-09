package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import expansioncontent.expansionContentMod;

import static collector.util.Wiz.applyToEnemy;
import static expansioncontent.expansionContentMod.loadJokeCardImage;

import java.util.ArrayList;

public class DoubleAct extends AbstractExpansionCard{
    public final static String ID = makeID("DoubleAct");

    private static final int downfallMagic = 0;

    public DoubleAct() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        //todo skill bg instead of power bg
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_donudeca.png", "expansioncontentResources/images/1024/bg_boss_donudeca.png");
        tags.add(expansionContentMod.STUDY_SHAPES);
        tags.add(expansionContentMod.STUDY);
        baseDownfallMagic = downfallMagic;
        baseMagicNumber = magicNumber = 0;
        loadJokeCardImage(this, "DoubleAct.png");
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new EnergizedBluePower(AbstractDungeon.player,  p.energy.energyMaster));
        applyToSelf(new DrawCardNextTurnPower(AbstractDungeon.player, p.gameHandSize));
    }

    @Override
    public void upp() {
upgradeBaseCost(2);
    }
}