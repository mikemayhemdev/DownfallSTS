package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;

@CardIgnore
public class SuperHexaguard extends AbstractExpansionCard {
    public final static String ID = makeID("SuperHexaguard");


    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;

    public SuperHexaguard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_HEXAGHOST);
        tags.add(expansionContentMod.STUDY);
        baseBlock = BLOCK;
        isEthereal = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        blck();
        atb(new DrawCardAction(p, 1));

    }

    public void triggerOnExhaust() {
        blck();
        atb(new DrawCardAction(AbstractDungeon.player, 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }

}


