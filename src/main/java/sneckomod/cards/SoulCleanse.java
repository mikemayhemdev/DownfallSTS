package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Snecko;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleHandAction;

public class SoulCleanse extends AbstractSneckoCard {

    public final static String ID = makeID("SoulCleanse");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public SoulCleanse() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
        exhaust = true;
        SneckoMod.loadJokeCardImage(this, "SoulCleanse.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new MuddleHandAction(true));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.exhaust = false;
        }
    }
}