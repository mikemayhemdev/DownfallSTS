package collector.cards.CollectorCards.Attacks;

import collector.actions.HellfireAction;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class Hellfire extends AbstractCollectorCard {
    public final static String ID = makeID("Hellfire");

    public Hellfire() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        magicNumber = baseMagicNumber = 3;
        damage = baseDamage = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new HellfireAction(p,damage, DamageInfo.DamageType.NORMAL,freeToPlayOnce, EnergyPanel.getCurrentEnergy(),upgraded));
    }

    @Override
    public void upp() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}