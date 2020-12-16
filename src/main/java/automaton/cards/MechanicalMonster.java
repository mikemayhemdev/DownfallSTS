package automaton.cards;

import automaton.cardmods.IncreasedDamageThisTurnMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MechanicalMonster extends AbstractBronzeCard {

    public final static String ID = makeID("MechanicalMonster");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 10;

    public MechanicalMonster() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        AbstractCard q = this;
        atb(new AbstractGameAction() {
            @java.lang.Override
            public void update() {
                isDone = true;
                CardModifierManager.addModifier(q, new IncreasedDamageThisTurnMod(q.baseDamage));
            }
        });
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}