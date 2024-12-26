package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.EvilWithinPower;

public class DashGenerateEvil extends AbstractExpansionCard {
    public static final String ID = makeID("DashGenerateEvil");

    private static final int MAGIC = 10;

    private static final int downfallMagic = 5;

    public DashGenerateEvil() {
        super(ID, 0, AbstractCard.CardType.POWER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_power.png", "expansioncontentResources/images/1024/bg_boss_power.png");
        this.baseDownfallMagic = 5;
        this.baseMagicNumber = this.magicNumber = 10;
        this.exhaust = true;
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "DashGenerateEvil.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.player.hasPower("expansioncontent:EvilWithinPower"))
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new EvilWithinPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new EvilWithinPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
        }
    }
}
