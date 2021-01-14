package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;

public class Hexaguard extends AbstractHexaCard {

    public final static String ID = makeID("Hexaguard");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;

    public Hexaguard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
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