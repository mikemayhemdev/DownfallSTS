package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import guardian.GuardianMod;
import guardian.cards.BodySlam;

import static guardian.GuardianMod.makeBetaCardPath;

public class SuperBodyCrash extends AbstractExpansionCard {
    public static final String ID = makeID("SuperBodyCrash");

    private static final int BLOCK = 7;

    public SuperBodyCrash() {
        super(ID, 1, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_guardian.png", "expansioncontentResources/images/1024/bg_boss_guardian.png");
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "SuperBodyCrash.png");
        this.baseBlock = 6;
        this.tags.add(expansionContentMod.STUDY_GUARDIAN);
        this.tags.add(expansionContentMod.STUDY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        this.baseDamage = p.currentBlock + this.block;
        calculateCardDamage(m);
        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)p, this.block));
        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.rawDescription = this.DESCRIPTION;
        initializeDescription();

    }

    public void applyPowers() {
        super.applyPowers();
        this.baseDamage = AbstractDungeon.player.currentBlock + this.block;
        super.applyPowers();
        this.rawDescription = this.DESCRIPTION;
        this.rawDescription += BodySlam.UPGRADED_DESCRIPTION;
        initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
