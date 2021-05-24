package collector.cards;

import collector.CollectorChar;
import collector.cards.AbilityCards.AllyDefend;
import collector.cards.AbilityCards.AllyStrike;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static collector.TorchChar.Abilities;

public class TagTeam extends AbstractCollectorCard {
    public final static String ID = makeID("TagTeam");

    public TagTeam() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        ArrayList<AbstractCard> NextChoices = new ArrayList<>();
        for (AbstractCard c : Abilities){
            if (c instanceof AllyStrike){
                AbstractCard defend = new AllyStrike(m);
                defend.applyPowers();
                defend.calculateCardDamage(m);
                NextChoices.add(defend);
            }
            if (c instanceof AllyDefend){
                AbstractCard defend = new AllyDefend();
                defend.applyPowers();
                NextChoices.add(defend);
            }
        }
        if (!CollectorChar.torch.isDead) {
            addToBot(new ChooseOneAction(NextChoices));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}