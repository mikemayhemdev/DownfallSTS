package collector.cards;

import collector.CollectorChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TagTeam extends AbstractCollectorCard {
    public final static String ID = makeID("TagTeam");

    public TagTeam() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        CollectorChar.TorchHead.CommandAt(m);
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }
}