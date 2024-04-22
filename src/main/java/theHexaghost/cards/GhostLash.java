package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class GhostLash extends AbstractHexaCard implements HexaPurpleTextInterface{

    public final static String ID = makeID("GhostLash");

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static int ethereal_inhand = 0;
    private boolean can_show = false;

    public GhostLash() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 3;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "GhostLash.png");
        this.keywords.add(downfallMod.keywords_and_proper_names.get("afterlife"));
    }

    public static int calculate_afterlifes(){
        if(!AbstractDungeon.overlayMenu.endTurnButton.enabled && ethereal_inhand != 0){  // this is to make sure it counts correct number of cards when afterlifed
            return ethereal_inhand;
        }else{
            ethereal_inhand = 0;
            for(AbstractCard c : AbstractDungeon.player.hand.group){
                if(c.isEthereal){
                    ethereal_inhand += 1;
                }
            }
        }
        return ethereal_inhand;
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
        if(AbstractDungeon.player.hasPower("Pen Nib") ){
            this.damage /= 2;
            dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
            this.damage *= 2;
        }else {
            dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        }
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
            applyPowers(); // to make the card show correct damage number when you draw it the first time, without this somehow when you draw the card for the first
                            // time each combat, it will only show the damage number based on cards already drawn, so if you draw another related card the damage won't
                            // be updated until you drag the card which calls applypowers(), I dont like this so added this update that always call applypowers()
                            // and added the condition variable so that it's only updated when the card is actually in your hand like how cards in vanilla behave
        }
    }
}