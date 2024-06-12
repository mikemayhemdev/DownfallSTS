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


    // to still show afterlife tooltip. because the format [purple]hexamod:afterlife[] doesnt get displayed correctly
    // we are only using [purple]afterlife[] here for easier text comprehension for new players, but doing this
    // means we dont have the keyword tooltip so we need to manually add it
    // but after I tried adding it in the constrcutor it turns out sometimes who knows why it wont be added
    // and this way seems to work
    @Override
    public void initializeDescription() {
        super.initializeDescription();
        String afterlife_name = downfallMod.keywords_and_proper_names.get("afterlife");
        this.keywords.add(downfallMod.keywords_and_proper_names.get("soulburn"));
        this.keywords.add(afterlife_name);
    }
}