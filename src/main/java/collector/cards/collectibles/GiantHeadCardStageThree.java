package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;

public class GiantHeadCardStageThree extends AbstractCollectibleCard {
    public final static String ID = makeID(GiantHeadCardStageThree.class.getSimpleName());
    // intellij stuff attack, all_enemy, uncommon, 55, 11, , , , 

    public GiantHeadCardStageThree() {
        super(ID, 3, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, CardColor.COLORLESS);
        baseDamage = 70;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void upp() {
        upgradeDamage(30);
    }
}