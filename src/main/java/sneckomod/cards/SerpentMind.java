package sneckomod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.SerpentMindPower;

public class SerpentMind extends AbstractSneckoCard {

    public final static String ID = makeID("SerpentMind");

    //stupid intellij stuff POWER, SELF, RARE

    private static final int MAGIC = 1;

    public SerpentMind() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;

        tags.add(BaseModCardTags.FORM);
        tags.add(SneckoMod.SNEKPROOF);
        SneckoMod.loadJokeCardImage(this, "SerpentMind.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SerpentMindPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            tags.remove(SneckoMod.SNEKPROOF);
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
