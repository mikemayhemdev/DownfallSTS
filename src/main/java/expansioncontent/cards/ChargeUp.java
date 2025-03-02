package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import guardian.GuardianMod;
import hermit.actions.ReduceDebuffsAction;

import static guardian.GuardianMod.makeBetaCardPath;

public class ChargeUp extends AbstractExpansionCard {
    public static final String ID = makeID("ChargeUp");

    private static final int BLOCK = 20;

    private static final int UPGRADE_BLOCK = 10;

    private static final int MAGIC = 2;

    public ChargeUp() {
        super(ID, 0, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_guardian.png", "expansioncontentResources/images/1024/bg_boss_guardian.png");
        this.tags.add(expansionContentMod.STUDY_GUARDIAN);
        this.tags.add(expansionContentMod.STUDY);
        this.baseMagicNumber = this.magicNumber = 2;
        this.exhaust = true;
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "BronzeArmor.png");

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ReduceDebuffsAction((AbstractCreature)AbstractDungeon.player, this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = this.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
