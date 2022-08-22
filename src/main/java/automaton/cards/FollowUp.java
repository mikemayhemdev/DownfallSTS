package automaton.cards;

import automaton.FunctionHelper;
import automaton.actions.RepeatCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FollowUp extends AbstractBronzeCard {

    public final static String ID = makeID("FollowUp");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;

    public FollowUp() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        AbstractCard q = this;
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (FunctionHelper.functionsCompiledThisTurn > 0){
                    att(new RepeatCardAction(m, q));
                }
            }
        });
    }

    @Override
    public void triggerOnGlowCheck() {
        if (FunctionHelper.functionsCompiledThisTurn > 0){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
    }
}