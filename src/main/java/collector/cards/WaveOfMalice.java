package collector.cards;

import basemod.abstracts.cardbuilder.CardBasic;
import basemod.abstracts.cardbuilder.actionbuilder.ActionBuilder;
import basemod.abstracts.cardbuilder.actionbuilder.AllEnemyActionBuilder;
import collector.CollectorMod;
import collector.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.isAfflicted;
import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;

public class WaveOfMalice extends AbstractCollectorCard {
    public final static String ID = makeID("WaveOfMalice");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public WaveOfMalice() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 13;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, FIRE);

        Wiz.forAllMonstersLiving(this::applyEffects);

    }

    public void applyEffects(AbstractMonster m){
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (isAfflicted(m)) {dmg(m, FIRE);}
            }
        });
    }

    public void upp() {
        upgradeDamage(4);
    }
}