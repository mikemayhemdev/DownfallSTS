package champ.cards;

import champ.actions.VigorWallopAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class HeavySlash extends AbstractChampCard {
    public final static String ID = makeID("HeavySlash");

    public HeavySlash() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 9;
        postInit();
        loadJokeCardImage(this, "HeavySlash.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new VigorWallopAction((AbstractCreature)m, (AbstractCreature)p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    public void upp() {
        upgradeDamage(3);
    }
}