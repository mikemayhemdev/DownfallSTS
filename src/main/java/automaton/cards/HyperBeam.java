package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import static automaton.AutomatonMod.makeBetaCardPath;

public class HyperBeam extends AbstractBronzeCard {

    public final static String ID = makeID("HyperBeam");

    //stupid intellij stuff attack, all_enemy, rare

    private static final int DAMAGE = 25;
    private static final int UPG_DAMAGE = 10;

    public HyperBeam() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        selfRetain = true;
      //  exhaust = true;
        baseMagicNumber = magicNumber = 5;
        cardsToPreview = new VoidCard();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("HyperBeam.png"));
    }

    /*
    public void onRetained() {
        this.addToBot(new ReduceCostAction(this));
    }

     */

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), magicNumber, false, true));
        allDmg(AbstractGameAction.AttackEffect.NONE);
    }

    public void upp() {
        upgradeDamage(10);
    }
}