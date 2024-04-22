package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class Hexaguard extends AbstractHexaCard implements HexaPurpleTextInterface {
    public final static String ID = makeID("Hexaguard");

    private static final int UPG_BLOCK = 3;

    public Hexaguard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 6;
        isEthereal = true;
        baseMagicNumber = magicNumber = 2;
        tags.add(HexaMod.AFTERLIFE);
        this.keywords.add(downfallMod.keywords_and_proper_names.get("afterlife"));
        HexaMod.loadJokeCardImage(this, "Hexaguard.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DrawCardAction(p, this.magicNumber));
    }

    public void afterlife() {
        blck();
//        atb(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}