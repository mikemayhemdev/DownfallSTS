package champ.cards;

import champ.ChampChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.EasyXCostAction;

public class SteelEdge extends AbstractChampCard {

    public final static String ID = makeID("SteelEdge");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 8;

    public SteelEdge() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        //   this.tags.add(downfallMod.BANNEDFORSNECKO);
        postInit();
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
        atb(new EasyXCostAction(this, (effect, params) -> {

                for (int i = 0; i < effect; i++) {
                    dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
                    combo();
                }
            return true;
        }));
    }


    public void upp() {
        upgradeDamage(3);
    }
}