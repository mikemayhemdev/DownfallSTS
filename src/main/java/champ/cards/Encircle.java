package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static champ.ChampMod.loadJokeCardImage;

public class Encircle extends AbstractChampCard {

    public final static String ID = makeID("Encircle");

    //stupid intellij stuff attack, all_enemy, uncommon

    private static final int DAMAGE = 5;

    public Encircle() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        // tags.add(ChampMod.COMBO);
        // tags.add(ChampMod.COMBOBERSERKER);
        baseMagicNumber = magicNumber = 1;
        postInit();
        loadJokeCardImage(this, "Encircle.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // if (upgraded) techique();

        atb(new SFXAction("ATTACK_WHIRLWIND"));
        atb(new VFXAction(new WhirlwindEffect(), 0.0F));
        allDmg(AbstractGameAction.AttackEffect.NONE);

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((!monster.isDeadOrEscaped())) {
                atb(new DrawCardAction(magicNumber));
            }
        }
    }

    public void upp() {
        // tags.add(ChampMod.TECHNIQUE);
        upgradeDamage(3);
    }
}