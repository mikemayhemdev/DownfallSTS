package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class FlamesFromBeyond extends AbstractHexaCard implements HexaPurpleTextInterface {

    public final static String ID = makeID("FlamesFromBeyond");

    public FlamesFromBeyond() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseBurn = burn = 10;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        this.keywords.add(downfallMod.keywords_and_proper_names.get("afterlife"));
        this.keywords.add(downfallMod.keywords_and_proper_names.get("soulburn"));
        HexaMod.loadJokeCardImage(this, "FlamesFromBeyond.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            burn(q, burn);
            burn(q, burn);
        }
    }

    @Override
    public void afterlife() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            burn(m, burn);
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(3);
        }
    }
}