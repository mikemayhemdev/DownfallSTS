package champ.cards;

import champ.ChampChar;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;
import downfall.actions.EasyXCostAction;

public class SteelEdge extends AbstractChampCard {

    public final static String ID = makeID("SteelEdge");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 10;

    public SteelEdge() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.COMBO);

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!(bcombo()) && !dcombo()) {
            this.cantUseMessage = ChampChar.characterStrings.TEXT[61];
            return false;
        }
        return super.canUse(p, m);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        String stance = p.stance.ID;
        atb(new EasyXCostAction(this, (effect, params) -> {

            for (int i = 0; i < effect; i++) {
                dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
                combo();

                atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        if (p.stance.ID.equals(NeutralStance.STANCE_ID) && !stance.equals(NeutralStance.STANCE_ID)){
                            atb(new ChangeStanceAction(stance));
                        }
                    }
                });

            }
            return true;
        }));
    }


    public void upp() {
        upgradeDamage(3);
    }
}