package champ.cards.stancecards;

import champ.ChampMod;
import champ.cards.AbstractChampCard;
import champ.vfx.DaggerSprayAnyColorEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class FanOfKnives extends AbstractChampCard {

    public final static String ID = makeID("FanOfKnives");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    public FanOfKnives() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, CardColor.COLORLESS);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        loadJokeCardImage(this, "FanOfKnives.png");
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new DaggerSprayAnyColorEffect(AbstractDungeon.getMonsters().shouldFlipVfx(), Color.FIREBRICK), 0.0F));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        atb(new VFXAction(new DaggerSprayAnyColorEffect(AbstractDungeon.getMonsters().shouldFlipVfx(), Color.RED), 0.0F));
        allDmg(AbstractGameAction.AttackEffect.NONE);

    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}