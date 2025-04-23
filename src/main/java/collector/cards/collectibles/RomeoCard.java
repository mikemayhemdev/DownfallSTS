package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import sneckomod.SneckoMod;

import static champ.ChampMod.vigor;
import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class RomeoCard extends AbstractCollectibleCard {
    public final static String ID = makeID(RomeoCard.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 5, 3

    public RomeoCard() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FreeAttackPower(p, 1));
        vigor(magicNumber);
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}