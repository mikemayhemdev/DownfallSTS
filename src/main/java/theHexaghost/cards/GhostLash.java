package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHexaghost.HexaMod;

public class GhostLash extends AbstractHexaCard {

    public final static String ID = makeID("GhostLash");

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static int afterlife_inhand = 0;
    private boolean can_show = false;

    public GhostLash() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 4;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "GhostLash.png");
    }

    public static int calculate_afterlifes(){
        if(!AbstractDungeon.overlayMenu.endTurnButton.enabled && afterlife_inhand != 0){
            return afterlife_inhand ;
        }else{
            afterlife_inhand = 0;
            for(AbstractCard c : AbstractDungeon.player.hand.group){
                if(c.hasTag(HexaMod.AFTERLIFE)){
                    afterlife_inhand += 1;
                }
            }
        }
        return afterlife_inhand ;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += calculate_afterlifes() * this.magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += calculate_afterlifes() * this.magicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        this.can_show = false;
    }

    @Override
    public void afterlife() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (m == null) return;
        this.calculateCardDamage(m);
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        this.can_show = false;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        this.can_show = true;
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        this.can_show = false;
    }

    @Override
    public void update() {
        super.update();
        if(can_show){
            applyPowers(); // to make the card show correct damage number when you draw it the first time
        }
    }
}