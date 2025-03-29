package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;

public class JousterCard extends AbstractCollectibleCard {
    public final static String ID = makeID(JousterCard.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 12, 3, 12, 3, , 

    public JousterCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = 12;
        baseBlock = 12;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (Wiz.getEnemies().size() == 1) {
            blck();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = Wiz.getEnemies().size() == 1 ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
    }
}