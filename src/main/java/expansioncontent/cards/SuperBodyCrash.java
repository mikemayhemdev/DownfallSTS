package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import downfall.util.CardIgnore;
import expansioncontent.expansionContentMod;
import hermit.actions.ReduceDebuffsAction;
import slimebound.powers.NextTurnGainStrengthPower;

import static guardian.cards.BodySlam.UPGRADED_DESCRIPTION;

public class SuperBodyCrash extends AbstractExpansionCard {
    public final static String ID = makeID("SuperBodyCrash");

    private static final int BLOCK = 7;

    public SuperBodyCrash() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_guardian.png", "expansioncontentResources/images/1024/bg_boss_guardian.png");
        baseBlock = 6;
        tags.add(expansionContentMod.STUDY_GUARDIAN);
        tags.add(expansionContentMod.STUDY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        this.baseDamage = p.currentBlock + this.block;
        calculateCardDamage(m);
        addToBot(new GainBlockAction(p, block));
        addToBot(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new DamageInfo(p, this.damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseDamage = AbstractDungeon.player.currentBlock + this.block;
        super.applyPowers();

        this.rawDescription = DESCRIPTION;
        this.rawDescription += UPGRADED_DESCRIPTION;
        initializeDescription();
    }



    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}


